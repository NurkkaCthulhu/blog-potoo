import React from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import './frontpage_style.css';
import BlogPost from './BlogPost';
import NewPost from './NewPost';
import PostLoader from './PostLoader';

function Index() {
    return <h1></h1>;
}

function App() {

    return (
        <Router>
            <div>
                <nav className="header">
                    <Link to="/">Sivun logo</Link>
                </nav>

                <Route path="/" exact component={Index} />
                <PostLoader />
                <div className="footer"><span>Blog Potoo, the blog of the future (2019)</span></div>
            </div>
        </Router>
    );
}


export default App;
