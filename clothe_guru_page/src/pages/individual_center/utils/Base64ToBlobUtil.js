function base64ToBlob(base64, contentType) {
    // 解码 Base64 字符串
    const byteCharacters = atob(base64);

    // 创建一个用于存储解码结果的数组
    const byteArrays = [];

    // 每次处理 512 字节
    const sliceSize = 512;
    for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
        const slice = byteCharacters.slice(offset, offset + sliceSize);

        // 创建一个新的 Uint8Array 来存储解码结果
        const byteNumbers = new Array(slice.length);
        for (let i = 0; i < slice.length; i++) {
            byteNumbers[i] = slice.charCodeAt(i);
        }

        const byteArray = new Uint8Array(byteNumbers);
        byteArrays.push(byteArray);
    }

    // 使用解码后的数据创建 Blob
    const blob = new Blob(byteArrays, { type: contentType });
    return blob;
}

export { base64ToBlob }