import api from "../api/axios";

export const getIncome = () => api.get("/incomes");

export const addIncome = (data) => api.post("/incomes", data);

export const updateIncome = (id, data) => api.put(`/incomes/${id}`, data);

export const deleteIncome = (id) => api.delete(`/incomes/${id}`);