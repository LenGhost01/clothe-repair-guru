import {v4 as uuidv4} from 'uuid'
import axios from "axios";
class MyUploadAdapter {
    constructor( loader ) {
        // The file loader instance to use during the upload.
        this.loader = loader;
    }

    upload() {
        return this.loader.file.then(file => new Promise(async (resolve, reject) => {
            try{
                let formData = new FormData()
                let suffix = file.name.split('.').pop()
                formData.append('uploadImage', file, `${uuidv4()}.${suffix}`)
                let result =await axios.post("/requests/imageUploader/upload",formData)
                console.log(result.data)
                resolve({
                    default: `http://192.168.32.141/images/${result.data}`
                })
            }catch (error){
                reject(error)
            }
        }))
    }

    abort() {
        // todo 传输中断执行逻辑
    }
}

export default MyUploadAdapter