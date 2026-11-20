import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../services/api";
import "../styles/EditAuthor.css";

function EditAuthor() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [message, setMessage] = useState("");

  useEffect(() => {
    getAuthor();
  }, []);

  const getAuthor = async () => {
    try {
      const response = await api.get(`/authors/${id}`);

      setName(response.data.name);
    } catch (error) {
      console.log(error);
      setMessage("Failed to load author.");
    }
  };

  const handleSubmit = async (e) => {
  e.preventDefault();

  const author = {
    name: name
  };

  try {
    await api.put(`/authors/${id}`, author);

    setMessage("Author updated successfully!");

    setTimeout(() => {
      navigate("/authors");
    }, 1000);
  } catch (error) {
    console.log(error);
    setMessage("Failed to update author.");
  }
};

  return (
    <div className="edit-author-page">
  <div className="edit-author-container">

        <h1>Edit Author</h1>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Author Name</label>

            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>

          <button type="submit">
            Update Author
          </button>

          {message && (
            <p className="form-message">{message}</p>
          )}
        </form>

      </div>
    </div>
  );
}

export default EditAuthor;