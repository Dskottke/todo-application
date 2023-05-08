import React from 'react';
import {useNavigate} from "react-router-dom";

function Form({submitButtonText, secondaryButtonText, submitFunction, navLink}) {
    const nav = useNavigate();
    const onFormSubmit = (e) => {
        e.preventDefault()
        const data = new FormData(e.currentTarget)
        const credentials = Object.fromEntries(data)
        submitFunction(credentials)
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
                <button className={"btn"} type={"submit"}>{submitButtonText}</button>
                <button onClick={() => nav(navLink)} className={"btn"} type={"button"}>{secondaryButtonText}</button>
            </div>
        </form>
    );
}

export default Form;