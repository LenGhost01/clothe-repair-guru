const animate_controller = (obj, animate_name, operation) => {
    if (operation === 1) {
        obj.classList.add(animate_name)
    } else {
        obj.classList.remove(animate_name)
    }
}

const animate_controller_double_stage = (obj, animate_name, before) => {
    obj.classList.remove(before)
    obj.classList.add(animate_name)
}

export {animate_controller, animate_controller_double_stage}