import { Link } from "react-router-dom";
import "../styles/Dashboard.css";
import RoleBased from "../components/RoleBased";

function Dashboard() {

  return (
    <div className="dashboard-page">
      
      <header className="dashboard-header">
        <h1>Library Management System</h1>

      </header>

      <main className="dashboard-content">

        <h2>Dashboard</h2>

        <p>Welcome to the Library Management System.</p>

        <div className="dashboard-menu">

          <Link to="/books" className="dashboard-card">
            <h3>Books</h3>
            <p>Manage library books</p>
          </Link>

          <Link to="/authors" className="dashboard-card">
            <h3>Authors</h3>
            <p>Manage authors</p>
          </Link>

        <RoleBased role="ADMIN">
          <Link to="/members" className="dashboard-card">
          <h3>Members</h3>
          <p>Manage library members</p>
          </Link>
        </RoleBased>

        <RoleBased role="ADMIN">
          <Link to="/borrowings" className="dashboard-card">
          <h3>Borrowings</h3>
          <p>Manage borrowed books</p>
          </Link>
        </RoleBased>

        <RoleBased role="ADMIN">
          <Link to="/fines" className="dashboard-card">
          <h3>Fines</h3>
          <p>Manage fines</p>
          </Link>
        </RoleBased>

         <Link to="/borrowings/add" className="dashboard-card">
          <h3>Borrow Book</h3>
          <p>Borrow a book from the library</p>
          </Link>

        </div>

      </main>

    </div>
  );
}

export default Dashboard;