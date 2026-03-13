<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="stat-card">
          <div class="stat-title">药品总数</div>
          <div class="stat-value">{{ stats.totalDrugs }}</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="stat-card warning">
          <div class="stat-title">即将过期</div>
          <div class="stat-value">{{ stats.expiringDrugs }}</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="stat-card danger">
          <div class="stat-title">已过期</div>
          <div class="stat-value">{{ stats.expiredDrugs }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="quick-actions" style="margin-top: 20px">
      <template #header>
        <span>快捷操作</span>
      </template>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-button type="primary" @click="$router.push('/drugs/add')" style="width: 100%">
            <el-icon><Plus /></el-icon> 添加药品
          </el-button>
        </el-col>
        <el-col :span="6">
          <el-button @click="$router.push('/drugs')" style="width: 100%">
            <el-icon><Search /></el-icon> 查看药品
          </el-button>
        </el-col>
        <el-col :span="6">
          <el-button @click="$router.push('/family')" style="width: 100%">
            <el-icon><User /></el-icon> 家庭成员
          </el-button>
        </el-col>
        <el-col :span="6">
          <el-button @click="$router.push('/categories')" style="width: 100%">
            <el-icon><Folder /></el-icon> 药品分类
          </el-button>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getDrugPage } from '@/api/drug'
import { getExpiringDrugs, getExpiredDrugs } from '@/api/drug'

const stats = ref({
  totalDrugs: 0,
  expiringDrugs: 0,
  expiredDrugs: 0
})

const loadStats = async () => {
  try {
    const [pageRes, expiringRes, expiredRes] = await Promise.all([
      getDrugPage({ pageSize: 1 }),
      getExpiringDrugs(7),
      getExpiredDrugs()
    ])
    stats.value.totalDrugs = pageRes.total
    stats.value.expiringDrugs = expiringRes?.length || 0
    stats.value.expiredDrugs = expiredRes?.length || 0
  } catch (error) {
    console.error('加载统计数据失败', error)
  }
}

onMounted(() => {
  loadStats()
})
</script>

<style scoped>
.stat-card {
  text-align: center;
}

.stat-title {
  color: #909399;
  font-size: 14px;
  margin-bottom: 10px;
}

.stat-value {
  font-size: 36px;
  font-weight: bold;
  color: #409EFF;
}

.stat-card.warning .stat-value {
  color: #E6A23C;
}

.stat-card.danger .stat-value {
  color: #F56C6C;
}

.quick-actions .el-button {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
}
</style>
