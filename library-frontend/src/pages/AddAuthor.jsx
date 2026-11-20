import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";
import "../styles/AddAuthor.css";

function AddAuthor() {
  const [name, setName] = useState("");
  const [message, setMessage] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const author = {
      name: name
    };

    try {
      await api.post("/authors", author);

      setMessage("Author added successfully!");

      setTimeout(() => {
        navigate("/authors");
      }, 1000);
    } catch (error) {
      console.log(error);
      setMessage("Failed to add author.");
    }
  };

  return (
    <div className="add-author-page">
  <div className="add-author-container">

        <h1>Add Author</h1>

        <form onSubmit={handleSubmit}>

          <div className="form-group">
            <label>Author Name</label>

            <input
              type="text"
              placeholder="Enter author name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>

          <button type="submit">
            Add Author
          </button>

          {message && (
            <p className="form-message">{message}</p>
          )}

        </form>

      </div>
    </div>
  );
}

export default AddAuthor;