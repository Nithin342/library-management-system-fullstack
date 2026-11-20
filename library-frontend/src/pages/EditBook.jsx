import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../services/api";
import "../styles/AddBook.css";

function EditBook() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [title, setTitle] = useState("");
  const [authorId, setAuthorId] = useState("");
  const [category, setCategory] = useState("");
  const [isbn, setIsbn] = useState("");
  const [price, setPrice] = useState("");
  const [quantity, setQuantity] = useState("");
  const [message, setMessage] = useState("");

  useEffect(() => {
  getBook();
}, []);

const getBook = async () => {
  try {
    const response = await api.get(`/books/${id}`);

    const book = response.data;

    setTitle(book.title);
    setAuthorId(book.authorId);
    setCategory(book.category);
    setIsbn(book.isbn);
    setPrice(book.price);
    setQuantity(book.quantity);
  } catch (error) {
    console.log(error);
    setMessage("Failed to load book.");
  }
};

const handleSubmit = async (e) => {
  e.preventDefault();

  const book = {
    title: title,
    authorId: Number(authorId),
    category: category,
    isbn: isbn,
    price: Number(price),
    quantity: Number(quantity)
  };

  try {
    await api.put(`/books/${id}`, book);

    setMessage("Book updated successfully!");

    setTimeout(() => {
      navigate("/books");
    }, 1000);
  } catch (error) {
    console.log(error);
    setMessage("Failed to update book.");
  }
};

  return (
    <div className="add-book-page">
      <div className="add-book-container">
        <h1>Edit Book</h1>

       <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Title</label>
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </div>

          <div className="form-group">
            <label>Author ID</label>
            <input
              type="number"
              value={authorId}
              onChange={(e) => setAuthorId(e.target.value)}
            />
          </div>

          <div className="form-group">
            <label>Category</label>
            <input
              type="text"
              value={category}
              onChange={(e) => setCategory(e.target.value)}
            />
          </div>

          <div className="form-group">
            <label>ISBN</label>
            <input
              type="text"
              value={isbn}
              onChange={(e) => setIsbn(e.target.value)}
            />
          </div>

          <div className="form-group">
            <label>Price</label>
            <input
              type="number"
              value={price}
              onChange={(e) => setPrice(e.target.value)}
            />
          </div>

          <div className="form-group">
            <label>Quantity</label>
            <input
              type="number"
              value={quantity}
              onChange={(e) => setQuantity(e.target.value)}
            />
          </div>

          <button type="submit">Update Book</button>

          {message && <p className="form-message">{message}</p>}
        </form>
      </div>
    </div>
  );
}

export default EditBook;