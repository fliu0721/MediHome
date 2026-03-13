<template>
  <div class="drug-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>药品列表</span>
          <el-button type="primary" @click="$router.push('/drugs/add')">
            <el-icon><Plus /></el-icon> 添加药品
          </el-button>
        </div>
      </template>

      <!-- 搜索栏 -->
      <el-form :inline="true" :model="queryForm" class="search-form">
        <el-form-item label="关键词">
          <el-input v-model="queryForm.keyword" placeholder="药品名称/厂家" clearable />
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="queryForm.categoryId" placeholder="选择分类" clearable>
            <el-option 
              v-for="item in categories" 
              :key="item.id" 
              :label="item.name" 
              :value="item.id" 
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryForm.status" placeholder="选择状态" clearable>
            <el-option label="正常" :value="0" />
            <el-option label="即将过期" :value="1" />
            <el-option label="已过期" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 数据表格 -->
      <el-table :data="drugList" v-loading="loading" border>
        <el-table-column prop="name" label="药品名称" min-width="150" />
        <el-table-column prop="specification" label="规格" min-width="120" />
        <el-table-column prop="manufacturer" label="生产厂家" min-width="150" />
        <el-table-column prop="expiryDate" label="有效期" width="120" />
        <el-table-column prop="stockQuantity" label="库存" width="100">
          <template #default="{ row }">
            {{ row.stockQuantity }}{{ row.stockUnit }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.status === 0" type="success">正常</el-tag>
            <el-tag v-else-if="row.status === 1" type="warning">即将过期</el-tag>
            <el-tag v-else-if="row.status === 2" type="danger">已过期</el-tag>
            <el-tag v-else type="info">已归档</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        :current-page="queryForm.pageNum"
        :page-size="queryForm.pageSize"
        :total="total"
        :page-sizes="[10, 20, 50]"
        layout="total, sizes, prev, pager, next"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getDrugPage, deleteDrug } from '@/api/drug'
import { getCategoryList } from '@/api/drugCategory'

const router = useRouter()
const loading = ref(false)
const drugList = ref([])
const total = ref(0)
const categories = ref([])

const queryForm = reactive({
  pageNum: 1,
  pageSize: 10,
  keyword: '',
  categoryId: null,
  status: null
})

const loadData = async () => {
  loading.value = true
  try {
    const res = await getDrugPage(queryForm)
    drugList.value = res.records
    total.value = res.total
  } finally {
    loading.value = false
  }
}

const loadCategories = async () => {
  const res = await getCategoryList()
  categories.value = res
}

const handleSearch = () => {
  queryForm.pageNum = 1
  loadData()
}

const resetQuery = () => {
  queryForm.keyword = ''
  queryForm.categoryId = null
  queryForm.status = null
  queryForm.pageNum = 1
  loadData()
}

const handleSizeChange = (val) => {
  queryForm.pageSize = val
  loadData()
}

const handleCurrentChange = (val) => {
  queryForm.pageNum = val
  loadData()
}

const handleEdit = (row) => {
  router.push(`/drugs/edit/${row.id}`)
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该药品吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteDrug(row.id)
    ElMessage.success('删除成功')
    loadData()
  })
}

onMounted(() => {
  loadData()
  loadCategories()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 20px;
}
</style>
