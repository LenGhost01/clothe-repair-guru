// https://restapi.amap.com/v3/ip?key=您的key&ip=获取的ip地址 // 高德地图ip查询api key必须是web服务(非js)


import axios from "axios";

let getIpLocation = async (host)=>{
    try {
        let ipLocation = await axios.get(`https://restapi.amap.com/v3/ip?key=0bbc4ead48639df41420ea0ae6aed233&ip=${host}`)
        return ipLocation
    }catch (e){
        console.log(e)
    }
}

let sendIpLocation = (userId,host)=>{
    console.log(userId,host)
    getIpLocation(host)
        .then((res)=>{
            let time = formatDate(new Date())
            let locationWrapper = {
                userId: userId,
                loginIp: host,
                loginLocation: res.data.province+res.data.city,
                loginTime: time
            }
            axios.get(`/requests/user/putUserLoginMsg?host=${locationWrapper.loginIp}&location=${locationWrapper.loginLocation}&` +
            `time=${locationWrapper.loginTime}&userId=${locationWrapper.userId}`).catch(err=>{
                // todo 尝试判断错误并进行错误处理
                console.log(err)
            })

        })
}
function formatDate(date) {
    const pad = (s) => s.toString().padStart(2, '0');

    let year = date.getFullYear();
    let month = pad(date.getMonth() + 1); // 月份是从 0 开始的
    let day = pad(date.getDate());
    let hour = pad(date.getHours());
    let minutes = pad(date.getMinutes());
    let seconds = pad(date.getSeconds());

    return `${year}-${month}-${day} ${hour}:${minutes}:${seconds}`;
}
export default {sendIpLocation}
