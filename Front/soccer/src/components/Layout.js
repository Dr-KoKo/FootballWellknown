import React, { Fragment } from "react";
function Layout(props) {
  return (
    <Fragment>
      <main>{props.chldren}</main>
    </Fragment>
  );
}

export default Layout;
