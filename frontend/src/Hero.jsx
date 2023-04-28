import React from 'react';
import {useGlobalContext} from "./Context.jsx";

function Hero(props) {

    const {openModal} = useGlobalContext()
    return (
        <section className={"hero-container"}>
            <div>
                <h1>ToDo-Application</h1>
                <p>Stay organized and focused with our easy-to-use Todo list.</p>
                <button
                    onClick={openModal}
                    className={"hero-btn"}
                    type={"button"}
                >
                    Add todo
                </button>
            </div>
        </section>
    );
}

export default Hero;