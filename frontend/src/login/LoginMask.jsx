import React from 'react';
import LoginForm from "./LoginForm.jsx";

function LoginMask(props) {
    return (
        <article className={"login-mask"}>
                <div className={"form-header"}>
                    <h1>Login</h1>
                    <div className={"head_underline"}></div>
                </div>
            <LoginForm/>
        </article>
    );
}

export default LoginMask;