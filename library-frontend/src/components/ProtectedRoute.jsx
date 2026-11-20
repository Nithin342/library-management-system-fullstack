import { Navigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

function ProtectedRoute({ children, role }) {
  const token = localStorage.getItem("token");
  const userRole = localStorage.getItem("role");

  // No token → login
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // Check whether JWT is valid
  try {
    const decodedToken = jwtDecode(token);

    if (!decodedToken.exp || decodedToken.exp * 1000 < Date.now()) {
      localStorage.removeItem("token");
      localStorage.removeItem("role");

      return <Navigate to="/login" replace />;
    }
  } catch (error) {
    localStorage.removeItem("token");
    localStorage.removeItem("role");

    return <Navigate to="/login" replace />;
  }

  // Check role
  if (role && userRole !== role) {
    return <Navigate to="/dashboard" replace />;
  }

  return children;
}

export default ProtectedRoute;