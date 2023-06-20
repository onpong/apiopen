import { PageContainer } from '@ant-design/pro-components';
import {Badge, Button, Card, Descriptions, Divider, Form, Image, message} from 'antd';
import React, { useEffect, useState } from 'react';
import {
  getInterfaceInfoByIdUsingGET,
  invokeInterfaceInfoUsingPOST,
} from '@/services/apiopen-backend/interfaceInfoController';
import { useParams } from '@@/exports';
import moment from "moment";
import ReactJson from 'react-json-view'
import VanillaJSONEditor from "./VanillaJSONEditor";
/**
 * 主页
 * @constructor
 */
const Index: React.FC = () => {
  // const { token } = theme.useToken();
  // const { initialState } = useModel('@@initialState');
  const [loading, setLoading] = useState(false);
  const [invokeLoading, setInvokeLoading] = useState(false);
  const [data, setdata] = useState<API.InterfaceInfo>();
  const [invokeRes, setInvokeRes] = useState<any>();
  const params = useParams();
  const param=new Object();
  const [content, setContent] = useState({
    json: param,
    text: undefined
  });
  const [img, setImg] = useState(null);



  const loadData = async () => {
    if (!params.id) {
      message.error('参数不存在');
      return;
    }
    setLoading(true);
    try {
      const res = await getInterfaceInfoByIdUsingGET({
        id: Number(params.id),
      });
      setdata(res.data);
    } catch (error: any) {
      message.error('请求失败' + error.message);
    }
    setLoading(false);
  };


  useEffect(() => {
    loadData();
  }, []);


  const onFinish = async (values: any) => {
    if (!params.id) {
      message.error('接口不存在');
      return;
    }
    let jsonValue= JSON.stringify(content.json);
    console.log(jsonValue);
    console.log(typeof jsonValue);
    //let replaceAfter;
    if(jsonValue == undefined || jsonValue == null || jsonValue === ''){
      jsonValue=JSON.parse(JSON.stringify(content.text));

    }
    setInvokeLoading(true);
    try {
      const res = await invokeInterfaceInfoUsingPOST({
        id: Number(params.id),
        userRequestParams:jsonValue
      });
      let json1;
      console.log(res);
      if(res.code===40100){
        json1=res;
        message.error("登录过期，请重新登录！")
      }
      console.log((params.id))
      // if(params.id==='1'){
      //   setImg(JSON.parse(res.data));
      // }else {
      //   console.log(res.data);
      //   json1=JSON.parse(res.data);
      // }
      console.log(res.data);
      json1=JSON.parse(res.data);
      console.log(typeof json1)
      console.log(json1)
      setInvokeRes(json1);

      message.success('修改成功');
    } catch (error: any) {
      message.error('操作失败' + error.message);
    }
    setInvokeLoading(false);
  };

  const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo);
  };

  return (
    <PageContainer title="查看接口文档">
      <Card>
        {data ? (
          <>
            <Descriptions title={data.name} bordered>
              <Descriptions.Item label="描述" span={2}>{data.description}</Descriptions.Item>
              <Descriptions.Item label="请求地址">{data.url}</Descriptions.Item>
              <Descriptions.Item label="请求方法">{data.method}</Descriptions.Item>
              <Descriptions.Item label="创建时间">{moment(data.createTime).format('YYYY-MM-DD HH:mm:ss')}</Descriptions.Item>
              <Descriptions.Item label="更新时间" span={2}>
                {moment(data.updateTime).format('YYYY-MM-DD HH:mm:ss')}
              </Descriptions.Item>
              <Descriptions.Item label="接口状态" span={3}>
                <Badge status={data.status===1?"success":"error"} text={data.status===1?"正常":"下线"} />
              </Descriptions.Item>
              <Descriptions.Item label="请求头">{data.requestHeader}</Descriptions.Item>
              <Descriptions.Item label="响应头">{data.responseHeader}</Descriptions.Item>
              <Descriptions.Item label="价格">免费</Descriptions.Item>
              <Descriptions.Item label="请求参数">
                <ReactJson name={false} src={JSON.parse(data.requestParams)} />
              </Descriptions.Item>
            </Descriptions>
          </>
        ) : (
          <>接口不存在</>
        )}
      </Card>
      <Divider/>

      <Card title="在线测试">
        <Form
          name="invoke"
          layout={"vertical"}
          onFinish={onFinish}
          onFinishFailed={onFinishFailed}
        >
          <Form.Item
            label="请求参数"
            name="userRequestParams"
          >
            <div className="my-editor">
              <VanillaJSONEditor
                content={content}
                onChange={setContent}
              />
            </div>
          </Form.Item>


          <Form.Item wrapperCol={{span: 16}}>
            <Button type="primary" htmlType="submit">
              调用
            </Button>
          </Form.Item>
        </Form>
      </Card>
      <Divider/>
      <Card title={"返回结果"} loading={invokeLoading}>
        { img  ?  <Image
          width={200}
          src={img.text}
        />:<ReactJson name={false} src={invokeRes} />}
      </Card>
    </PageContainer>
  );
};

export default Index;
