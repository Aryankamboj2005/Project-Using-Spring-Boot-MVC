import React, { useEffect, useState, useContext } from "react";
import { Link, useLocation } from "react-router-dom";
import AppContext from "../Context/Context";
import axios from "../axios"; // Using local axios config with credentials

const Navbar = ({ onSelectCategory, onSearch }) => {
  const { user } = useContext(AppContext);
  const location = useLocation();
  const getInitialTheme = () => {
    const storedTheme = localStorage.getItem("theme");
    return storedTheme ? storedTheme : "light-theme";
  };

  const [theme, setTheme] = useState(getInitialTheme());
  const [input, setInput] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [noResults, setNoResults] = useState(false);
  const [isScrolled, setIsScrolled] = useState(false);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [showSearchResults, setShowSearchResults] = useState(false);

  const handleLogout = () => {
    window.location.href = "http://localhost:8080/logout";
  };

  useEffect(() => {
    const handleScroll = () => {
      setIsScrolled(window.scrollY > 20);
    };
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  useEffect(() => {
    document.body.className = theme;
  }, [theme]);

  const handleChange = async (value) => {
    setInput(value);
    if (value.length >= 1) {
      setShowSearchResults(true);
      try {
        const response = await axios.get(
          `http://localhost:8080/api/products/search?keyword=${value}`
        );
        setSearchResults(response.data);
        setNoResults(response.data.length === 0);
      } catch (error) {
        console.error("Error searching:", error);
      }
    } else {
      setShowSearchResults(false);
      setSearchResults([]);
      setNoResults(false);
    }
  };

  const handleCategorySelect = (category) => {
    onSelectCategory(category);
    setIsDropdownOpen(false);
  };

  const toggleTheme = () => {
    const newTheme = theme === "dark-theme" ? "light-theme" : "dark-theme";
    setTheme(newTheme);
    localStorage.setItem("theme", newTheme);
  };

  const categories = ["Laptop", "Headphone", "Mobile", "Electronics", "Toys", "Fashion"];

  return (
    <header className={`navbar-premium ${isScrolled ? "navbar-scrolled" : ""}`}>
      <div className="nav-content">
        <Link to="/" className="nav-brand">
          <i className="bi bi-lightning-charge-fill"></i>
          ALPHA<span>SHOP</span>
        </Link>

        <nav className="nav-main-links">
          <Link to="/" className={location.pathname === "/" ? "active" : ""}>Home</Link>
          <Link to="/add_product" className={location.pathname === "/add_product" ? "active" : ""}>Add Product</Link>
          
          <div className="dropdown">
            <button className="dropdown-toggle" onClick={() => setIsDropdownOpen(!isDropdownOpen)}>
              Categories
            </button>
            {isDropdownOpen && (
              <ul className="dropdown-menu-premium">
                {categories.map((category) => (
                  <li key={category}>
                    <button className="dropdown-item" onClick={() => handleCategorySelect(category)}>
                      {category}
                    </button>
                  </li>
                ))}
              </ul>
            )}
          </div>
        </nav>


        <div className="search-wrapper">
          <div className="search-input-group">
            <i className="bi bi-search search-icon"></i>
            <input
              type="text"
              placeholder="Search products..."
              value={input}
              onChange={(e) => handleChange(e.target.value)}
              onFocus={() => input.length > 0 && setShowSearchResults(true)}
              onBlur={() => setTimeout(() => setShowSearchResults(false), 200)}
            />
          </div>

          {showSearchResults && (
            <div className="search-results-floating">
              {searchResults.length > 0 ? (
                searchResults.map((result) => (
                  <Link 
                    key={result.id} 
                    to={`/product/${result.id}`} 
                    className="result-item"
                    onClick={() => setShowSearchResults(false)}
                  >
                    <i className="bi bi-arrow-right-short"></i>
                    <span>{result.name}</span>
                  </Link>
                ))
              ) : (
                noResults && <p className="no-results-message">No results found</p>
              )}
            </div>
          )}
        </div>

        <div className="nav-right-actions">
          <button className="icon-action-btn" onClick={toggleTheme}>
            {theme === "dark-theme" ? <i className="bi bi-moon-stars"></i> : <i className="bi bi-sun"></i>}
          </button>
          
          <Link to="/cart" className="icon-action-btn">
            <i className="bi bi-bag"></i>
            <span className="cart-count">3</span>
          </Link>

          {user ? (
            <div className="user-profile-nav">
              <span className="user-name">Hi, {user.name || user.login}</span>
              <button className="logout-btn" onClick={handleLogout}>Logout</button>
            </div>
          ) : (
            <div className="login-buttons">
              <a href="http://localhost:8080/oauth2/authorization/linkedin" className="login-icon-btn linkedin">
                <i className="bi bi-linkedin"></i>
              </a>
              <a href="http://localhost:8080/oauth2/authorization/github" className="login-icon-btn github">
                <i className="bi bi-github"></i>
              </a>
            </div>
          )}
        </div>
      </div>
    </header>
  );
};

export default Navbar;

