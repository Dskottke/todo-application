import axios from "axios";

const customToDoFetch = axios.create({
        baseURL: "/api/todo"
    },
    {withCredentials: true});
const customUserFetch = axios.create({
        baseURL: "/api/user"
    },
    {withCredentials: true});

export default {customToDoFetch, customUserFetch};


