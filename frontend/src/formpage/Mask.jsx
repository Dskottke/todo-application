import React from 'react';
import Form from "./Form.jsx";

function Mask({title, submitButtonText, secondaryButtonText, submitFunction, navLink}) {
    return (
        <article className={"login-signup-mask"}>
            <div className={"form-header"}>
                <h1>{title}</h1>
                <div className={"head_underline"}></div>
            </div>
            <Form
                title={title}
                submitButtonText={submitButtonText}
                secondaryButtonText={secondaryButtonText}
                submitFunction={submitFunction}
                navLink={navLink}
            />
        </article>
    );
}

export default Mask;