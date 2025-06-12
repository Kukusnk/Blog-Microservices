//
// import React from 'react';
// import './App.css';
// import RegistrationForm from './components/RegistrationForm';
// import SearchForm from './components/SearchForm';
//
// const App: React.FC = () => {
//     return (
//         <div className="App">
//             <h1>User Management</h1>
//             <div style={{ marginBottom: '40px' }}>
//                 <RegistrationForm />
//             </div>
//             <div>
//                 <SearchForm />
//             </div>
//         </div>
//     );
// };
//
// export default App;

import React from 'react';
import './App.css';
import RegistrationForm from './components/RegistrationForm';
import SearchForm from './components/SearchForm';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import LoginForm from "./components/LoginForm.tsx";
import GetMe from "./components/GetMe.tsx";

const App: React.FC = () => {
    return (
        <Router>
            <div className="App">
                <h1>Управление пользователями</h1>
                <nav>
                    <ul>
                        <li>
                            <Link to="/register">Регистрация</Link>
                        </li>
                        <li>
                            <Link to="/search">Поиск</Link>
                        </li>
                        <li>
                            <Link to="/login">Login</Link>
                        </li>
                        <li>
                            <Link to="/getCurrentUser">Get Me</Link>
                        </li>
                    </ul>
                </nav>

                <Routes>
                    <Route path="/register" element={<RegistrationForm />} />
                    <Route path="/search" element={<SearchForm />} />
                    <Route path="/login" element={<LoginForm />} />
                    <Route path="/getCurrentUser" element={<GetMe />} />
                </Routes>
            </div>
        </Router>
    );
};

export default App;
