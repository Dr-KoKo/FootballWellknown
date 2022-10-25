import React, { Fragment } from 'react';
import { Link } from 'react-router-dom';

const Layout = (props) => {
  return (
    <Fragment>
        <main>{props.children}</main>
    </Fragment>
  );
};

export default Layout;