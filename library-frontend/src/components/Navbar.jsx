import { Link, useNavigate } from "react-router-dom";
import RoleBased from "./RoleBased";
import "../styles/Navbar.css";

function Navbar() {
  const navigate = useNavigate();

  const role = localStorage.getItem("role");

 const handleLogout = () => {
  const confirmLogout = window.confirm(
    "Are you sure you want to logout?"
  );

  if (!confirmLogout) {
    return;
  }

  localStorage.removeItem("token");
  localStorage.removeItem("role");

  navigate("/login");
};

  return (
    <nav className="navbar">
      <Link to="/dashboard">Dashboard</Link>

      <Link to="/books">Books</Link>

      <Link to="/authors">Authors</Link>

      <RoleBased role="ADMIN">
        <Link to="/members">Members</Link>
      </RoleBased>

      <RoleBased role="ADMIN">
        <Link to="/borrowings">Borrowings</Link>
      </RoleBased>

      <RoleBased role="ADMIN">
        <Link to="/fines">Fines</Link>
      </RoleBased>

      <Link to="/borrowings/add">Borrow Book</Link>

    <span className="user-role">
  Role: {role}
</span>

      <button onClick={handleLogout}>
        Logout
      </button>
    </nav>
  );
}

export default Navbar;