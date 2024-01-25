function init(jsonObject,defaultValue){
    for(let key in jsonObject){
        jsonObject[key] = defaultValue
    }
}

export default {init}