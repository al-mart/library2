```jsx
function App() {
  return (
    <BrowserRouter>
      <Switch>
        <Route path="/people">
          <PeopleContainer />
        </Route>
        <Link to="/people">People</Link>
      </Switch>
    </BrowserRouter>
  );
}
export default App;
```

BrowserRouter - links our code and the HTML5 history API in the browser.
