import React from 'react';
import {useGlobalContext} from "./Context.jsx";
import {HiOutlineXMark} from 'react-icons/hi2'
import AddForm from "./AddForm.jsx";

function AddModal() {

    const {isModalOpen, closeModal} = useGlobalContext();

    return (
        <div className={isModalOpen ? 'modal-overlay show-modal' : 'modal-overlay'}>
            <div className={"modal-container"}>
                <button
                    className={"modal-btn"}
                    type={"button"}><HiOutlineXMark
                    onClick={closeModal}
                />
                </button>
                <AddForm/>
            </div>

        </div>
    );
}

export default AddModal;