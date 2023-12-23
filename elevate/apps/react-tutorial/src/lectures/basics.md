# Basics

Adding classes as importing css files. What about SCSS ? 
- class is className in react.
- javascript syntax is written as { } in jsx.
- data is passed to child components as props in functional components
```jsx
const B = (props) => {
    return (
        <div>
          <span>{props.title}</span>
          <span>{props.price}</span>
        </div>
    );
}

const A = () => {
    const data = {title: 'TITLE', price: 15};
    return (
      <B title={data.title} price={data.price} />
    );
}
```

- props.children renders content between the tags, also can use props.className
- in older days it was obligatory to import React in files that had jsx. Now it is not obligatory.
- jsx is just React.createElement("tag", {}, ...children);
jsx is syntactic sugar. and we return one React.createElement() so we need 
one parent elements in jsx
- on{EVENT} (onClick) binds event to function => onClick={functionRef}
- Hooks must be called directly inside functional components, (cannot call inside nested functions)
- useState return [currentState, setState]
- component re-renders when state or props change, re-renders only the component whose state or props change.
- setState is async , it schedules state change, which is set before re-render.
-  setState accepts function6 which is used to update state if we depend on previous state
remember that setState is async. so we won't mess up with parallel updates.
```jsx
const A = () => {
    const [state, setState] = useState({});
    
    const updateState = (updatedData) => {
        setState((prevState) => {
            return {
                ...prevState,
              'key': updatedData
            }
        })
    } 
}
```
- child to parent communication 
```jsx
const B = (props) => {
    saveClickedHandler = () => {
        props.onSaveEvent("Data");
    }
    return (
      <button onClick={saveClickedHandler}>Save</button>
    );
}

const A = () => {
    const saveEventHandler = (data) => console.log(data);
    return (
      <B onSaveEvent={saveEventHandler}/>
    );
}
```
- controlled component is a component that don't hold any logic 
it just receives props and emit data change. And updated its ui from props
- dumb or stateless component is a component that just draws ui , do not have state 


- list is rendered as , key is obligatory, so we won't run into bugs and
- all the list don't rerender on array change
```jsx
const A = () => {
  const items = [1, 2, 3];
  return (
      <div>
        {items.map((item) => <div key={item.id}>{item}</div>)}
      </div>
  );
  // because react renders [<div>A</div>, <div>B</div>] as adjacent elements
}
```
- conditional elements can be added with ? : ternary or (&& and ) operators.
- style accepts object of key value styles.
- we can use <React.Fragment> or default <> tags to wrap and dont use <div>
-  to create portal just use in jsx  ReactDom.createPortal(<JSX>, document.getElementById(parentNode));
- useRef() Hook create a ref and we pass it to <div ref={myRef}></div>
10 chapter
