<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="组织编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'organizationCode', validatorRules.organizationCode]" placeholder="请输入组织编号"></a-input>
        </a-form-item>
        <a-form-item label="组织名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'organizationName', validatorRules.organizationName]" placeholder="请输入组织名称"></a-input>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'status', validatorRules.status]" placeholder="请输入状态"></a-input>
        </a-form-item>
        <a-form-item label="地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'address', validatorRules.address]" placeholder="请输入地址"></a-input>
        </a-form-item>
        <a-form-item label="省" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'province', validatorRules.province]" placeholder="请输入省"></a-input>
        </a-form-item>
        <a-form-item label="市" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'city', validatorRules.city]" placeholder="请输入市"></a-input>
        </a-form-item>
        <a-form-item label="区" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'area', validatorRules.area]" placeholder="请输入区"></a-input>
        </a-form-item>
        <a-form-item label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'callPerson', validatorRules.callPerson]" placeholder="请输入联系人"></a-input>
        </a-form-item>
        <a-form-item label="联系方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'phone', validatorRules.phone]" placeholder="请输入联系方式"></a-input>
        </a-form-item>
        <a-form-item label="是否客户" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'isCustomer', validatorRules.isCustomer]" placeholder="请输入是否客户"></a-input>
        </a-form-item>
        <a-form-item label="是否供应商" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'isSupplier', validatorRules.isSupplier]" placeholder="请输入是否供应商"></a-input>
        </a-form-item>
        <a-form-item label="是否货主" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'isWarehouse', validatorRules.isWarehouse]" placeholder="请输入是否货主"></a-input>
        </a-form-item>
        <a-form-item label="是否承运商" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'isApplication', validatorRules.isApplication]" placeholder="请输入是否承运商"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'note', validatorRules.note]" placeholder="请输入备注"></a-input>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  
  export default {
    name: "WmsOrganizationModal",
    components: { 
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules:{
        organizationCode:{},
        organizationName:{},
        status:{},
        address:{},
        province:{},
        city:{},
        area:{},
        callPerson:{},
        phone:{},
        isCustomer:{},
        isSupplier:{},
        isWarehouse:{},
        isApplication:{},
        note:{},
        },
        url: {
          add: "/wmsOrganization/wmsOrganization/add",
          edit: "/wmsOrganization/wmsOrganization/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'organizationCode','organizationName','status','address','province','city','area','callPerson','phone','isCustomer','isSupplier','isWarehouse','isApplication','note'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'organizationCode','organizationName','status','address','province','city','area','callPerson','phone','isCustomer','isSupplier','isWarehouse','isApplication','note'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>