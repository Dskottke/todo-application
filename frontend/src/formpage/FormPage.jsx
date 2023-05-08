import React from 'react';
import Mask from "./Mask.jsx";

function FormPage({title, submitButtonText, secondaryButtonText, submitFunction, navLink}) {
    return (
        <section className={"page"}>
            <Mask
                title={title}
                submitButtonText={submitButtonText}
                secondaryButtonText={secondaryButtonText}
                submitFunction={submitFunction}
                navLink={navLink}
            />
        </section>
    );
}

export default FormPage;