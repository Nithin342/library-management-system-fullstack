import {BrowserRouter,Routes,Route,Navigate,useLocation } from "react-router-dom";

import Login from "./pages/Login";
import Books from "./pages/Books";
import Dashboard from "./pages/Dashboard";
import AddBook from "./pages/AddBook";
import EditBook from "./pages/EditBook";
import Authors from "./pages/Authors";
import AddAuthor from "./pages/AddAuthor";
import EditAuthor from "./pages/EditAuthor";
import AuthorBooks from "./pages/AuthorBooks";
import Members from "./pages/Members";
import AddMember from "./pages/AddMember";
import EditMember from "./pages/EditMember";
import MemberBorrowings from "./pages/MemberBorrowings";
import Borrowings from "./pages/Borrowings";
import BorrowBook from "./pages/BorrowBook";
import Fines from "./pages/Fines";
import NotFound from "./pages/NotFound";

import ProtectedRoute from "./components/ProtectedRoute";
import Navbar from "./components/Navbar";
import "./dark-theme.css";

function Home() {
  const token = localStorage.getItem("token");

  return token ? (
    <Navigate to="/dashboard" replace />
  ) : (
    <Navigate to="/login" replace />
  );
}

function Layout() {
  useLocation();

  const token = localStorage.getItem("token");

  return (
    <>
      {token && <Navbar />}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route  path="/books"  element={ <ProtectedRoute> <Books /> </ProtectedRoute> } />
        <Route path="/dashboard" element={ <ProtectedRoute> <Dashboard /> </ProtectedRoute> } />
       <Route path="/books/add" element={ <ProtectedRoute> <AddBook /> </ProtectedRoute> }  />
        <Route path="/books/edit/:id" element={ <ProtectedRoute> <EditBook /> </ProtectedRoute> } />
        <Route path="/authors" element={ <ProtectedRoute> <Authors /> </ProtectedRoute> } />
        <Route path="/authors/add" element={ <ProtectedRoute> <AddAuthor /> </ProtectedRoute> } />
        <Route path="/authors/edit/:id" element={ <ProtectedRoute> <EditAuthor /> </ProtectedRoute> } />
        <Route path="/authors/:id/books" element={ <ProtectedRoute> <AuthorBooks /> </ProtectedRoute> } />
       <Route path="/members" element={ <ProtectedRoute role="ADMIN"> <Members /> </ProtectedRoute> } />
        <Route path="/members/add" element={ <ProtectedRoute role="ADMIN"> <AddMember /> </ProtectedRoute> } />
        <Route path="/members/edit/:id" element={ <ProtectedRoute role="ADMIN"> <EditMember /> </ProtectedRoute> } />
       <Route path="/members/:id/borrowings" element={ <ProtectedRoute role="ADMIN"> <MemberBorrowings /> </ProtectedRoute> } />
        <Route path="/borrowings" element={ <ProtectedRoute role="ADMIN"> <Borrowings /> </ProtectedRoute> } />
        <Route path="/borrowings/add" element={ <ProtectedRoute> <BorrowBook /> </ProtectedRoute> } />
        <Route path="/fines" element={ <ProtectedRoute role="ADMIN"> <Fines /> </ProtectedRoute> } />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </>
  );
}

function App() {
  return (
    <BrowserRouter>
      <Layout />
    </BrowserRouter>
  );
}

export default App;