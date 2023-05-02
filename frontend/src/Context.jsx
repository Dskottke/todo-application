import {createContext, useContext, useMemo, useState} from "react";
import {DONE, IN_PROGRESS, OPEN} from "./types.js";

export const GlobalContext = createContext({})
export const useGlobalContext = () => useContext(GlobalContext)

const AppContext = ({children}) => {

    const [isModalOpen, setIsModalOpen] = useState(false)
    const [toDos, setToDos] = useState({})

    const openAmount = useMemo(() => {
        return Object.values(toDos).filter((toDo) => toDo.status === OPEN).length
    }, [toDos])

    const inProgressAmount = useMemo(() => {
        return Object.values(toDos).filter((toDo) => toDo.status === IN_PROGRESS).length
    }, [toDos])

    const doneAmount = useMemo(() => {
        return Object.values(toDos).filter((toDo) => toDo.status === DONE).length
    }, [toDos])

    const openModal = () => {
        setIsModalOpen(true)
    }
    const closeModal = () => {
        setIsModalOpen(false)
    }

    const value = useMemo(() => {
        return {isModalOpen, openModal, closeModal, toDos, setToDos, openAmount, inProgressAmount, doneAmount}
    }, [isModalOpen, toDos])

    return (
        <GlobalContext.Provider value={value}>
            {children}
        </GlobalContext.Provider>
    );

}
export default AppContext