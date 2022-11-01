import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { BrowserRouter } from "react-router-dom";

import { Provider } from "react-redux";
import rootReducer from "./modules";
import { persistReducer, persistStore } from "redux-persist";
import { configureStore } from "@reduxjs/toolkit";
import storage from "redux-persist/lib/storage";
import promiseMiddleware from "redux-promise";
import reduxThunk from "redux-thunk";
import { PersistGate } from "redux-persist/integration/react";

const persistConfig = {
  key: "root",
  storage, // localstorage에 저장
};

const persistedReducer = persistReducer(persistConfig, rootReducer);

export const store = configureStore({
  reducer: persistedReducer,
  middleware: [promiseMiddleware, reduxThunk],
});

const persistor = persistStore(store);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <Provider store={store}>
    <PersistGate persistor={persistor}>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </PersistGate>
  </Provider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
