import {useGlobalContext} from "./Context.jsx";
import {useMutation, useQuery, useQueryClient} from "react-query";
import customToDoFetch from "./customFetches.js";
import {toast} from "react-toastify";
import {useEffect, useMemo, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";

export const useSortData = (toDos) => {

    const openList = toDos.filter(todo => todo.status === "OPEN")
    const inProgressList = toDos.filter(todo => todo.status === "IN_PROGRESS")
    const closed = toDos.filter(todo => todo.status === "DONE")

    return {openList, inProgressList, closed}
}
export const useFetchToDos = () => {
    const {setToDos} = useGlobalContext()

    const {isSuccess, isLoading, data, error} = useQuery({
        queryKey: ['todos'], queryFn: () => customToDoFetch.get(""), onSuccess: (data) => {
            setToDos(data.data)
        }
    })
    return {isSuccess, isLoading, data, error}
}


export const useAddToDo = () => {
    const queryClient = useQueryClient()
    const {mutate: createTask, isLoading} = useMutation({
        mutationFn: (newTodo) => {
            return customToDoFetch.post('', newTodo)
        }, onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['todos']})
            toast.success("New todo added!")
        }, onError: (error) => {
            toast.error(error.response.data)
        }

    })
    return {createTask, isLoading}
}

export const useAdvanceToDo = () => {
    const queryClient = useQueryClient()

    const {mutate: advanceToDo} = useMutation({
        mutationFn: (id) => customToDoFetch.put(`/${id}`), onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['todos']})
        }
    })
    return {mutate: advanceToDo}
}

export const useDeleteToDo = () => {
    const queryClient = useQueryClient()
    const {mutate: deleteToDo} = useMutation({
        mutationFn: (id) => customToDoFetch.delete(`/${id}`), onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['todos']})
        }
    })
    return {mutate: deleteToDo}
}
export const useDateFormatter = (creationDate, dueDate) => {
    const creationDateFormatted = useMemo(() => {
        return new Date(creationDate).toLocaleDateString("de-DE", {
            year: "numeric", month: "long", day: "numeric"
        })
    }, [creationDate])

    const dueDateFormatted = useMemo(() => {
        return new Date(dueDate).toLocaleDateString("de-DE", {
            year: "numeric", month: "long", day: "numeric"
        })
    }, [dueDate])

    const daysLeft = useMemo(() => {
        return Math.floor((new Date(dueDate) - new Date()) / (1000 * 60 * 60 * 24) + 1);
    }, [dueDate, creationDate]);

    return {creationDateFormatted, dueDateFormatted, daysLeft}
}

export const useGetColor = (daysLeft) => {
    const getColor = (daysLeft) => {
        if (daysLeft >= 14) {
            return "#48b748"
        }
        if (daysLeft >= 7) {
            return "#ec9900"
        } else {
            return "#d93a3a"
        }
    }
    return getColor(daysLeft)
}

export const useAuth = () => {
    const {setCurrentUser} = useGlobalContext();
    const [user, setUser] = useState();
    const navigate = useNavigate();

    useEffect(() => {
        axios.get("/api/user/me").then(res => {
            if (res.data.username === "anonymousUser") {
                navigate("/login");
            }
            setCurrentUser(res.data);
            setUser(res.data);
        }).catch(e => {
            if (e.response.status === 401) {
                navigate("/login");
            }
        });
    }, [navigate]);

    return user;

}
export const useUnconfirmedUsers = () => {
    const {setUnconfirmedUsers} = useGlobalContext()
    useEffect(() => {
        axios.get("/api/user/confirm/list")
            .then((response) => {
                setUnconfirmedUsers(response.data)
            })
    }, [])

}