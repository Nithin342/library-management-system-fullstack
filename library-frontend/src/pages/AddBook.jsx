import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";
import "../styles/AddBook.css";

function AddBook() {

  const [title, setTitle] = useState("");
  const [authorId, setAuthorId] = useState("");
  const [category, setCategory] = useState("");
  const [isbn, setIsbn] = useState("");
  const [price, setPrice] = useState("");
  const [quantity, setQuantity] = useState("");

  const [message, setMessage] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {

      const book = {
        title: title,
        authorId: Number(authorId),
        category: category,
        isbn: isbn,
        price: Number(price),
        quantity: Number(quantity)
      };

      await api.post("/books", book);

      setMessage("Book added successfully!");

      setTimeout(() => {
        navigate("/books");
      }, 1000);

   } catch (error) {

  console.log(error);

  if (error.response && error.response.data) {

    const errors = error.response.data;

    if (typeof errors === "object") {
      const messages = Object.values(errors);
      setMessage(messages.join(" • "));
    } else {
      setMessage(errors);
    }

  } else {

    setMessage("Failed to add book.");

  }

}
  };

 return (
  <div className="add-book-page">
  <div className="add-book-container"> 

      <h1>Add Book</h1>

      <form onSubmit={handleSubmit}>

        <div className="form-group">
          <label>Title</label>
          <input
            type="text"
            placeholder="Enter book title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label>Author ID</label>
          <input
            type="number"
            placeholder="Enter author ID"
            value={authorId}
            onChange={(e) => setAuthorId(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label>Category</label>
          <input
            type="text"
            placeholder="Enter category"
            value={category}
            onChange={(e) => setCategory(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label>ISBN</label>
          <input
            type="text"
            placeholder="Enter ISBN"
            value={isbn}
            onChange={(e) => setIsbn(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label>Price</label>
          <input
            type="number"
            placeholder="Enter price"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label>Quantity</label>
          <input
            type="number"
            placeholder="Enter quantity"
            value={quantity}
            onChange={(e) => setQuantity(e.target.value)}
          />
        </div>

        <button type="submit">
          Add Book
        </button>

      </form>

      {message && (
        <p className="form-message">{message}</p>
      )}

    </div>

  </div>
);
}

export default AddBook;