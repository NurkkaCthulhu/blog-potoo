import React from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import './css/frontpage_style.css';
import PostLoader from './PostLoader';
import NewPostForm from './NewPostForm';

function Index() {
    return <PostLoader />;
}

function NewPost() {
    return <NewPostForm/>
}

function App() {

    return (
        <Router>
            <div>
                <nav className="header">
                    <Link to="/">Sivun logo</Link>
                    <Link to="/newpost" className="newpostlink">Add blog post...</Link>
                </nav>

                <Route path="/" exact component={Index} />
                <Route path="/newpost" exact component={NewPost} />

                <div className="footer"><span>Blog Potoo, the blog of the future (2019)</span></div>
            </div>
        </Router>
    );
}


export default App;
