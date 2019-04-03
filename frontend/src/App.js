import React from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import './css/frontpage_style.css';
import PostLoader from './PostLoader';
import NewPostForm from './NewPostForm';
import BlogPost from './BlogPost';
import SearchBar from './SearchBar';


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
                    <Link to="/"><i className='fab fa-earlybirds'></i>Blog Potoo</Link>
                    <Link to="/modifypost/new" className="newpostlink">Add blog post...</Link>
                </nav>
                <div className="blog-posts">
                    <SearchBar />
                    <Route path="/" exact component={Index} />
                    <Route path="/search/:search" component={PostLoader} />
                    <Route path="/modifypost/:id" component={NewPostForm} />
                    <Route path={"/blogposts/:id"} component={BlogPost}/>
                </div>
                <div className="footer"><span>Blog Potoo, the blog of the future (2019)</span></div>
            </div>
        </Router>
    );
}


export default App;
