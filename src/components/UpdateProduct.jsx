import React, { useState, useEffect, useContext } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "../axios";
import AppContext from "../Context/Context";

const UpdateProduct = () => {
  const { user } = useContext(AppContext);
  const { id } = useParams();
  const navigate = useNavigate();
  const [product, setProduct] = useState({
    name: "",
    brand: "",
    desc: "",
    price: "",
    category: "",
    stockQuantity: "",
    releaseDate: "",
    available: false,
  });
  const [image, setImage] = useState(null);

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await axios.get(`/product/${id}`);
        setProduct(response.data);
      } catch (error) {
        console.error("Error fetching product:", error);
      }
    };
    fetchProduct();
  }, [id]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProduct({ ...product, [name]: value });
  };

  const handleImageChange = (e) => {
    setImage(e.target.files[0]);
  };

  const submitHandler = (event) => {
    event.preventDefault();
    if (!user) {
      alert("Please login first to update a product");
      return;
    }
    const formData = new FormData();
    formData.append("imageFile", image);
    formData.append(
      "product",
      new Blob([JSON.stringify(product)], { type: "application/json" })
    );

    axios
      .put(`/product/${id}`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        alert("Product updated successfully");
        navigate("/");
      })
      .catch((error) => {
        console.error("Error updating product:", error);
        alert("Error updating product");
      });
  };

  return (
    <div className="container">
      <div className="center-container">
        <form className="row g-3 pt-5" onSubmit={submitHandler}>
          <div className="col-md-6">
            <label className="form-label"><h6>Name</h6></label>
            <input
              type="text"
              className="form-control"
              placeholder={product.name}
              onChange={handleInputChange}
              value={product.name}
              name="name"
            />
          </div>
          <div className="col-md-6">
            <label className="form-label"><h6>Brand</h6></label>
            <input
              type="text"
              name="brand"
              className="form-control"
              placeholder={product.brand}
              value={product.brand}
              onChange={handleInputChange}
            />
          </div>
          <div className="col-12">
            <label className="form-label"><h6>Description</h6></label>
            <input
              type="text"
              className="form-control"
              placeholder={product.desc}
              value={product.desc}
              name="desc"
              onChange={handleInputChange}
            />
          </div>
          <div className="col-5">
            <label className="form-label"><h6>Price</h6></label>
            <input
              type="number"
              className="form-control"
              onChange={handleInputChange}
              value={product.price}
              name="price"
            />
          </div>
          <div className="col-md-6">
            <label className="form-label"><h6>Category</h6></label>
            <select
              className="form-select"
              value={product.category}
              onChange={handleInputChange}
              name="category"
            >
              <option value="">Select category</option>
              <option value="Laptop">Laptop</option>
              <option value="Headphone">Headphone</option>
              <option value="Mobile">Mobile</option>
              <option value="Electronics">Electronics</option>
              <option value="Toys">Toys</option>
              <option value="Fashion">Fashion</option>
            </select>
          </div>
          <div className="col-md-4">
            <label className="form-label"><h6>Stock Quantity</h6></label>
            <input
              type="number"
              className="form-control"
              onChange={handleInputChange}
              value={product.stockQuantity}
              name="stockQuantity"
            />
          </div>
          <div className="col-md-4">
            <label className="form-label"><h6>Release Date</h6></label>
            <input
              type="date"
              className="form-control"
              value={product.releaseDate}
              name="releaseDate"
              onChange={handleInputChange}
            />
          </div>
          <div className="col-md-4">
            <label className="form-label"><h6>Image</h6></label>
            <input
              className="form-control"
              type="file"
              onChange={handleImageChange}
            />
          </div>
          <div className="col-12">
            <div className="form-check">
              <input
                className="form-check-input"
                type="checkbox"
                name="available"
                checked={product.available}
                onChange={(e) =>
                  setProduct({ ...product, available: e.target.checked })
                }
              />
              <label className="form-check-label">Product Available</label>
            </div>
          </div>
          <div className="col-12">
            <button type="submit" className="btn btn-primary">Update Product</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default UpdateProduct;
