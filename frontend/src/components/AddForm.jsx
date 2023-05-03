import React from 'react';
import {useGlobalContext} from "../Context.jsx";
import {useMutation, useQueryClient} from "react-query";
import customToDoFetch from "../customFetches.js";
import {toast} from "react-toastify";
import {useAddToDo} from "../hooks.js";

function AddForm(props) {

    const {closeModal} = useGlobalContext()

    const {createTask, isLoading} = useAddToDo()

    const onFormSubmit = (e) => {
        e.preventDefault()
        const data = new FormData(e.currentTarget)
        const todo = Object.fromEntries(data)
        createTask(todo)
        closeModal();
    }

    return (
        <form className='form' onSubmit={onFormSubmit}>
            <div className={'form-header'}>
                <h4>new todo</h4>
                <div className={"head_underline"}></div>
            </div>
            <div className='form-row'>
                <label htmlFor='title' className='form-label'>
                    title
                </label>
                <input
                    type='text'
                    className='form-input'
                    id='title'
                    name='title'
                />
            </div>
            <div className='form-row'>
                <label htmlFor='description' className='form-label'>
                    description
                </label>
                <input
                    type='text'
                    className='form-input'
                    id='description'
                    name='description'
                />
            </div>

            <button type='submit' className='btn' disabled={isLoading}>
                submit
            </button>
        </form>
    );
}

export default AddForm;