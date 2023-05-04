import React from 'react';

function LoginForm(props) {
    return (
        <form className={"form"}>
            <div className={"form-row"}>
                <label className={"form-label"} htmlFor="username">Username</label>
                <input className={"form-input"} type="text" id={"username"} name={"username"}/>
            </div>
            <div className={"form-row"}>
                <label className={"form-label"} htmlFor="password">Password</label>
                <input className={"form-input"} type="password" id={"password"} name={"password"}/>
            </div>
            <div className={"form-action"}>
                <button className={"btn"} type={"submit"}>Login</button>
                <button className={"btn"} type={"submit"}>Register</button>
            </div>
        </form>
    );
}

export default LoginForm;