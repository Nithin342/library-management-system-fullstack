import { useNavigate } from "react-router-dom";

function NotFound() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const handleNavigation = () => {
    if (token) {
      navigate("/dashboard");
    } else {
      navigate("/login");
    }
  };

  return (
    <div>
      <h1>404 - Page Not Found</h1>

      <p>
        The page you are looking for does not exist.
      </p>

      <button onClick={handleNavigation}>
        {token ? "Go to Dashboard" : "Go to Login"}
      </button>
    </div>
  );
}

export default NotFound;