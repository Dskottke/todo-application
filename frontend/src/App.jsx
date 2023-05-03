
import Navbar from "./components/Navbar.jsx";
import Hero from "./components/Hero.jsx";
import BoardOverview from "./components/BoardOverview.jsx";
import AddModal from "./components/AddModal.jsx";
import {useGlobalContext} from "./Context.jsx";
import {ToastContainer} from "react-toastify";

function App() {

    const {isModalOpen} = useGlobalContext()

    return (
        <div className="App">
            <Navbar/>
            {isModalOpen && <AddModal/> }
            <Hero/>
            <BoardOverview/>
            <ToastContainer position={"top-center"}/>
        </div>
    )
}

export default App
