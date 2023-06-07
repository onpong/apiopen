export default [
  { name: '欢迎页面',path: '/', icon: 'smile', component: './Index' },
  { name: '查看接口',path: '/interface_info/:id', icon: 'smile', component: './InterfaceInfo', hideInMenu:true },
  { name: '登录', path: '/user', layout: false, routes: [{ path: '/user/login', component: './User/Login' }] },

  {
    path: '/admin',
    icon: 'crown',
    access: 'canAdmin',
    name: '管理员页面',
    routes: [
      //{ name:'二级管理页',path: '/admin', redirect: '/admin/sub-page' },
      { icon: 'table', path: '/admin/interface_info', component: './Admin/InterfaceInfo' ,name: '接口管理',},
    ],
  },
  //{ path: '/', redirect: '/welcome' },
  { path: '*', layout: false, component: './404' },
];
