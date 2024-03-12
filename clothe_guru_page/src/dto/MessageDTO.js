class MessageDTO{
    constructor(type,content,sender,receiver,sendTime) {
        this.type=type
        this.content=content
        this.sender=sender
        this.receiver=receiver
        this.sendTime=sendTime
    }
}

export default MessageDTO