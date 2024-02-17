import {message} from "ant-design-vue";

export default function errPrompt(error){
    if (error.response) {
        // 请求已发出，服务器以状态码响应不在2xx的范围
        console.error("An error occurred:", error.response.status, error.response.data);
        message.warning(`请求错误: ${error.response.status}, ${error.response.data.message}`);
    } else if (error.request) {
        // 请求已发出，但未收到响应
        console.error("No response received:", error.request);
        message.warning("无法连接到服务器，请检查您的网络连接或稍后再试。");
    } else {
        // 发送请求时出了点问题
        console.error("Error:", error.message);
        message.warning("请求失败，请稍后再试。");
    }
}