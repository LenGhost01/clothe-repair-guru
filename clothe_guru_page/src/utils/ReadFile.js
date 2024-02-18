import axios from "axios";
import { v4 as uuidv4 } from 'uuid';

let counter = -1
const readFile = async function (url) {
    try {
        // 尝试获取资源
        let response = await axios.get(url, {responseType: 'blob'});
        // 请求成功，将Blob数据转换成File数据
        const blob = response.data; // 获取Blob对象
        const filename = url.split('/').pop(); // 从URL中获取文件名
        const file = new File([blob], filename, {type: blob.type}); // 创建File对象
        const fileObject = {
            uid: counter--, // 建议使用负数或唯一标识符，以避免与正常上传的文件冲突
            name: filename,
            status: 'done', // 设置为'done'表示文件已经上传
            url: URL.createObjectURL(blob), // 为文件生成一个临时URL，用于预览等
            originFileObj: file, // 可选，如果需要原始File对象
        };
        return fileObject; // 这里你得到了文件的Blob数据
    } catch (error) {
        // 处理错误
        if (error.response) {
            // 服务器返回了一个状态码超出了2xx的范围
            console.log(error.response.status); // 可以看到具体的错误状态码
            console.log(error.response.data); // 服务器可能提供的错误信息
            // 根据状态码做具体的错误处理
            if (error.response.status === 404) {
                console.error("文件不存在");
            } else {
                console.error("发生了其他错误");
            }
        } else if (error.request) {
            // 请求已经发出，但没有收到响应
            console.error("未收到服务器响应");
        } else {
            // 发生了其他问题
            console.error('Error', error.message);
        }
        // 根据需要，可以在这里抛出错误或返回null
        return null; // 或者抛出异常，让调用者处理
    }

}

export default readFile