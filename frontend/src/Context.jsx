import {createContext, useContext, useMemo, useState} from "react";
import {DONE, IN_PROGRESS, OPEN} from "./Data.js";
import axios from "axios";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";

export const GlobalContext = createContext({})
export const useGlobalContext = () => useContext(GlobalContext)

const AppContext = ({children}) => {
    const navigate = useNavigate()
    const [isModalOpen, setIsModalOpen] = useState(false)
    const [toDos, setToDos] = useState({})
    const [currentUser, setCurrentUser] = useState(null)

    const fetchUser = (credentials) => {

        axios.get("/api/user/", {
            auth: {
                username: credentials.username,
                password: credentials.password
            }
        })
            .then((response) => {
                if (response.status === 200) {
                    navigate("/")
                }

            }).catch((error) => {
            if (error.response.status === 401)
                toast.error("Wrong username or password")
        })
    }
    const signUpUser = (credentials) => {
        axios.post("/api/user/", {
            username: credentials.username,
            password: credentials.password
        }).then((response) => {
            if (response.status === 201) {
                toast.success("user  " + response.data + " created , you can login now")
                navigate("/login")
            }
        }).catch((error) => {
            if (error.response.status === 400)
                toast.error("Username already exists")
        })


    }

    const getCurrentUser = useMemo(() => {
        return currentUser
    }, [currentUser])

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
        return {
            isModalOpen,
            openModal,
            closeModal,
            toDos,
            setToDos,
            openAmount,
            inProgressAmount,
            doneAmount,
            fetchUser,
            setCurrentUser,
            getCurrentUser,
            signUpUser
        }
    }, [isModalOpen, toDos])

    return (
        <GlobalContext.Provider value={value}>
            {children}
        </GlobalContext.Provider>
    );

}

export default AppContext