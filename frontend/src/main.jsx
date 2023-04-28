import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './index.css'
import {QueryClient, QueryClientProvider} from "react-query";
import AppContext from "./Context.jsx";

const client = new QueryClient();
ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <QueryClientProvider client={client}>
            <AppContext>
                <App/>
            </AppContext>
        </QueryClientProvider>
    </React.StrictMode>
)
