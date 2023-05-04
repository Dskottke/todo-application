import React from 'react';
import {useGlobalContext} from "../Context.jsx";

function LoginForm(props) {

    const {fetchUser} = useGlobalContext()
    const onFormSubmit = (e) => {
        e.preventDefault()
        const data = new FormData(e.currentTarget)
        const credentials = Object.fromEntries(data)
        fetchUser(credentials)
    }


    return (
        <form onSubmit={onFormSubmit} className={"form"}>
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
                <button className={"btn"} type={"button"}>Register</button>
            </div>
        </form>
    );
}

export default LoginForm;