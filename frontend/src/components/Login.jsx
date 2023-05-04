import React from 'react';

function Login() {
    return (
        <div className={"login"}>
            <h1>Login</h1>
            <form className={"form"}>
                <div className={"form-row"}>
                    <label htmlFor="username">Username</label>
                    <input type="text" id={"username"} name={"username"}/>
                </div>
                <div className={"form-row"}>
                    <label htmlFor="password">Password</label>
                    <input type="password" id={"password"} name={"password"}/>
                </div>
                <button type={"submit"}>Login</button>
            </form>
        </div>
    );
}

export default Login;