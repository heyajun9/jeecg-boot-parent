<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>

          <a-col :span="12">
            <a-form-item label="出库单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'pickticketCode', validatorRules.pickticketCode]" placeholder="请输入出库单号"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="单据类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'orderType', validatorRules.orderType]" placeholder="请输入单据类型"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="业务类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'serviceType', validatorRules.serviceType]" placeholder="请输入业务类型"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="单据日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择单据日期" v-decorator="[ 'pickticketDate', validatorRules.pickticketDate]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="到货日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择到货日期" v-decorator="[ 'arriveDate', validatorRules.arriveDate]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="收货人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'arrivor', validatorRules.arrivor]" placeholder="请输入收货人"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="收货地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'address', validatorRules.address]" placeholder="请输入收货地址"></a-input>
            </a-form-item>
          </a-col>
        
          <a-col :span="12">
            <a-form-item label="联系方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'phone', validatorRules.phone]" placeholder="请输入联系方式"></a-input>
            </a-form-item>
          </a-col>
        
        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="出库单明细" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="wmsPickticketDetailTable.loading"
            :columns="wmsPickticketDetailTable.columns"
            :dataSource="wmsPickticketDetailTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
      </a-tabs>

    </a-spin>
  </a-modal>
</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import JDate from '@/components/jeecg/JDate'  

  export default {
    name: 'WmsPickticketModal',
    mixins: [JEditableTableMixin],
    components: {
      JDate,
    },
    data() {
      return {
        labelCol: {
          span: 6
        },
        wrapperCol: {
          span: 16
        },
        labelCol2: {
          span: 3
        },
        wrapperCol2: {
          span: 20
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          pickticketCode:{},
          orderType:{},
          serviceType:{},
          pickticketDate:{},
          arriveDate:{},
          arrivor:{},
          address:{},
          phone:{},
        },
        refKeys: ['wmsPickticketDetail', ],
        tableKeys:['wmsPickticketDetail', ],
        activeKey: 'wmsPickticketDetail',
        // 出库单明细
        wmsPickticketDetailTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '物料编码',
              key: 'itemCode',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '物料名称',
              key: 'itemName',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '物料类型',
              key: 'itemType',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '物料标签',
              key: 'itemBarcode',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '包装',
              key: 'itemPackage',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '数量',
              key: 'num',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '单位',
              key: 'unit',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
            {
              title: '备注',
              key: 'note',
              type: FormTypes.input,
              defaultValue: '',
              placeholder: '请输入${title}',
            },
          ]
        },
        url: {
          add: "/pickticket/wmsPickticket/add",
          edit: "/pickticket/wmsPickticket/edit",
          wmsPickticketDetail: {
            list: '/pickticket/wmsPickticket/queryWmsPickticketDetailByMainId'
          },
        }
      }
    },
    methods: {
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'pickticketCode','orderType','serviceType','pickticketDate','arriveDate','arrivor','address','phone')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.wmsPickticketDetail.list, params, this.wmsPickticketDetailTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          wmsPickticketDetailList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      }
      
      
    }
  }
</script>

<style scoped>
</style>