import axios from "axios";

async function getIpClient() {
    try {
        const response = await axios.get('https://api.ipify.org?format=json');
        return response
    } catch (error) {
        console.error(error);
    }
}

export default {getIpClient}