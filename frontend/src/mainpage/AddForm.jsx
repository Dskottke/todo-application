import React from 'react';
import {useGlobalContext} from "../Context.jsx";
import {useAddToDo} from "../hooks.js";
import {minDate} from "../Data.js";

function AddForm() {

    const {closeModal} = useGlobalContext()

    const {mutate: createTask, isLoading} = useAddToDo()

    const onFormSubmit = (e) => {
        e.preventDefault()
        const data = new FormData(e.currentTarget)
        createTask(Object.fromEntries(data))
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
            <div className='form-row'>
                <label htmlFor='description' className='form-label'>
                    due-date
                </label>
                <input
                    type='date'
                    min={minDate}
                    defaultValue={minDate}
                    className='form-input'
                    id='dueDate'
                    name='dueDate'
                />
            </div>
            <button type='submit' className='btn' disabled={isLoading}>
                submit
            </button>
        </form>
    );
}

export default AddForm;