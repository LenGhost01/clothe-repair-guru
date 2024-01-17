package com.chenhaozhe.clothe_guru_code.services.impl;


import com.chenhaozhe.clothe_guru_code.exception.CustomInputMismatchException;
import com.chenhaozhe.clothe_guru_code.exception.DatabaseNotChangeException;
import com.chenhaozhe.clothe_guru_code.mapper.UserMapper;
import com.chenhaozhe.clothe_guru_code.model.converter.UserConverter;
import com.chenhaozhe.clothe_guru_code.model.dto.UserLoginMailDTO;
import com.chenhaozhe.clothe_guru_code.model.dto.UserLoginNormalDTO;
import com.chenhaozhe.clothe_guru_code.model.entity.UserEntity;
import com.chenhaozhe.clothe_guru_code.model.vo.UserRegisterVo;
import com.chenhaozhe.clothe_guru_code.model.vo.UserVo;
import com.chenhaozhe.clothe_guru_code.services.UserServices;
import com.chenhaozhe.clothe_guru_code.util.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class UserServicesImpl implements UserServices {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Value("${customConst.defaultPageSize}")
    private Short defaultPageSize;
    @Override
    public void InsertUserToDatabase(UserRegisterVo registerVo) {
        // 由系统分配一个id，再去除vo对象中对应的参数。
        Long id = new SnowFlake(1,1).nextId();
        Integer returnState = userMapper.insertUserToUsers(id, registerVo.getUsername(), registerVo.getPassword(), registerVo.getNickname(), registerVo.getEmail());
        if (returnState == 0){
            throw new DatabaseNotChangeException("插入数据失败，请呼叫管理员查询问题");
        }
    }

    @Override
    public void SendCaptcha(String mail) {
        String captcha = GenerateCaptcha.generate();
        String subject = "您本次的验证码是：";
        String text = """
                本次请求的邮件验证码为:""" + captcha + """
                本验证码 5 分钟内有效，请及时输入。（请勿泄露此验证码）
                """;
        GenerateMailSender.send(subject, text, mail);
        // 将发送后的验证码添加到redis服务器中，并设置过期时间为5分钟
        redisTemplate.opsForValue().set(mail, captcha, Duration.ofSeconds(5 * 60));
    }

    @Override
    public String mailCheck(String mail, String captcha) {
        Object storageCaptcha = redisTemplate.opsForValue().get(mail);
        if(Objects.equals(storageCaptcha,null)){
            throw new CustomInputMismatchException("邮箱不存在或者验证码过时，请重试！！！");
        }
        if(!Objects.equals(captcha,String.valueOf(storageCaptcha))){
            throw new CustomInputMismatchException("验证码错误，请检查！！！");
        }
        UserEntity userEntity = userMapper.selectUserByEmail(mail);
        if (Objects.equals(userEntity,null)){
            throw new CustomInputMismatchException("邮箱对应用户不存在！！！");
        }
        return generateUserMsgJson(userEntity);
    }

    @Override
    public String usernameAndPasswordCheck(String username, String password) {
        UserEntity userEntity = userMapper.selectUserByUsername(username);
        if (Objects.equals(userEntity,null)){
            throw new CustomInputMismatchException("登录用户不存在，请检查输入是否正确！！！");
        }
        if (!Objects.equals(password,userEntity.getPassword())){
            throw new CustomInputMismatchException("密码验证错误，请检查密码输入！！！");
        }
        return generateUserMsgJson(userEntity);
    }

    @Override
    public void deleteUser(Long userId) {
        userMapper.deleteUser(userId);
    }

    @Override
    public String AlterUserCheckPassword(String oldPassword, UserEntity user) {
        UserEntity entity = userMapper.selectUserByUserId(user.getUserId());
        if(!Objects.equals(entity.getPassword(),user.getPassword())){
            //用户输入的旧密码和数据库中的密码对应不上
            throw new CustomInputMismatchException("密码验证错误，请重新输入旧密码！！！");
        }
        // 不推荐使用删除再添加的方式，在高并发的场景下无法保证操作的原子性
        Integer returnState = userMapper.updateUser(user);
        if (returnState<1){
            throw new DatabaseNotChangeException("账号没有被正确修改，请致电管理员！！！");
        }

        UserVo userVo = updateUserById(user.getUserId());
        return JackonUtil.ObjectToJSON(userVo);
    }

    @Override
    public List<UserVo> getAllUsers(Integer page) {
        Integer offset = defaultPageSize*page;
        return userMapper.selectUsers(defaultPageSize,offset).stream().map(item -> UserConverter.convertToVO(item)).toList();
    }

    @Override
    public List<UserVo> getAllUsersByKeyWord(String keyWord, Integer page) {
        Integer offset = defaultPageSize*page;
        return userMapper.selectUsers(defaultPageSize,offset,"%"+keyWord+"%").stream().map(item -> UserConverter.convertToVO(item)).toList();
    }

    //↓ 下面是一些通用的方法
    public String generateUserMsgJson(UserEntity userEntity){
        UserVo userVo = UserConverter.convertToVO(userEntity);
        String token = JwtTokenUtil.generateToken(userEntity.getUserId());
        Map map = new HashMap();
        map.put("user",userVo);
        map.put("token",token);
        // 以用户的id为键，userVo为值保存在缓存中。
        getUserById(userVo.getUserId());
        return JackonUtil.MapToJson(map);
    }

    @Cacheable(key="#uid",value = "userCache")
    @Override
    public UserVo getUserById(Long uid){
        //用户登录成功后，将用户信息送入缓存
        UserVo userVo = UserConverter.convertToVO(userMapper.selectUserByUserId(uid));
        return userVo;
    }

    @CachePut(key="#uid",value = "userCache")
    public UserVo updateUserById(Long uid){
        // 用户执行修改操作后，更新缓存中的数据信息。
        UserVo userVo = UserConverter.convertToVO(userMapper.selectUserByUserId(uid));
        return userVo;
    }

    @CacheEvict(key="#uid")
    @Override
    public void userEvict(Long uid){} //用户退出登录时，删除缓存中的信息
}
