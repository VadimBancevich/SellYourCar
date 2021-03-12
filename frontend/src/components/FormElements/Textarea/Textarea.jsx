import React from "react";

const Textarea = (props) => {
    const {placeholder} = props;

    return (
        <textarea className={props.className} placeholder={placeholder} {...props.input}/>
    )
}

export default Textarea;