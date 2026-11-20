import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../services/api";
import RoleBased from "../components/RoleBased";
import "../styles/Authors.css";

function Authors() {
  const [authors, setAuthors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    getAuthors();
  }, []);

  const getAuthors = async () => {
    try {
      const response = await api.get("/authors");
      setAuthors(response.data);
    } catch (error) {
      console.log(error);
      setError("Failed to load authors.");
    } finally {
      setLoading(false);
    }
  };

  const viewBooks = (id) => {
  navigate(`/authors/${id}/books`);
};

  if (loading) {
    return <h2>Loading authors...</h2>;
  }

  if (error) {
    return <h2>{error}</h2>;
  }

  const deleteAuthor = async (id) => {
  const confirmDelete = window.confirm(
    "Are you sure you want to delete this author?"
  );

  if (!confirmDelete) {
    return;
  }

  try {
    await api.delete(`/authors/${id}`);

    setAuthors(authors.filter((author) => author.id !== id));
  } catch (error) {
    console.log(error);
    setError("Failed to delete author.");
  }
};
return (
  <div className="authors-page">

    <div className="authors-header">
      <h1>Authors</h1>
    </div>

    <div className="author-actions">

      <button
        className="back-dashboard-btn"
        onClick={() => navigate("/dashboard")}
      >
        ← Back to Dashboard
      </button>

      <RoleBased role="ADMIN">
        <Link to="/authors/add" className="add-author-btn">
          + Add Author
        </Link>
      </RoleBased>

    </div>

    <p className="total-authors">
      Total Authors: {authors.length}
    </p>

    <div className="authors-table-container">

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {authors.map((author) => (
            <tr key={author.id}>
              <td>{author.id}</td>
              <td>{author.name}</td>
              <td>

                <RoleBased role="ADMIN">
                  <button
                    className="edit-author-btn"
                    onClick={() =>
                      navigate(`/authors/edit/${author.id}`)
                    }
                  >
                    Edit
                  </button>
                </RoleBased>

                <RoleBased role="ADMIN">
                  <button
                    className="delete-author-btn"
                    onClick={() => deleteAuthor(author.id)}
                  >
                    Delete
                  </button>
                </RoleBased>

                <button
                  className="view-books-btn"
                  onClick={() => viewBooks(author.id)}
                >
                  View Books
                </button>

              </td>
            </tr>
          ))}
        </tbody>
      </table>

    </div>

  </div>
);
}

export default Authors;